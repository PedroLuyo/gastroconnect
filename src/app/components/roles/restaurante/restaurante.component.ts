import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RestaurantService } from './restaurant.service';
import { AuthService } from '../../../services/auth/authService';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-restaurante',
  templateUrl: './restaurante.component.html',
  styleUrls: ['./restaurante.component.css']
})
export class RestauranteComponent implements OnInit {
  restaurants: any[] = [];
  restaurantForm: FormGroup;
  isEditMode: boolean = false;
  identifier: number | null = null;
  loading: boolean = true;
  showForm: boolean = false;
  logoPreview: string | null = null;
  backgroundPreview: string | null = null;

  constructor(
    private fb: FormBuilder,
    private restaurantService: RestaurantService,
    private authService: AuthService
  ) {
    this.restaurantForm = this.fb.group({
      name: ['', Validators.required],
      businessInfo: this.fb.group({
        ruc: ['', [Validators.required, Validators.pattern('^[0-9]{11}$')]],
        businessName: ['', Validators.required],
        businessType: ['', Validators.required],
        category: [[], Validators.required]
      }),
      contact: this.fb.group({
        phone: ['', [Validators.required, Validators.pattern('^[0-9]{9}$')]],
        email: ['', [Validators.required, Validators.email]],
        website: [''],
        socialMedia: this.fb.group({
          facebook: [''],
          instagram: [''],
          x: [''],
          youtube: ['']
        })
      }),
      location: this.fb.group({
        address: ['', Validators.required],
        district: ['', Validators.required],
        city: ['', Validators.required],
        country: ['', Validators.required],
        reference: ['']
      }),
      features: this.fb.group({
        capacity: [null, [Validators.required, Validators.min(1)]],
        hasParking: [false],
        hasWifi: [false],
        acceptsReservations: [false],
        hasDelivery: [false],
        hasTakeout: [false],
        hasMenu: [false],
        hasCarta: [false]
      }),
      logoFile: [null],
      backgroundFile: [null]
    });
  }

  ngOnInit(): void {
    this.loadRestaurants();
  }

  async loadRestaurants(): Promise<void> {
    try {
      const uid = await this.authService.getUserUid();
      this.restaurantService.getRestaurantByUid(uid).subscribe({
        next: (data) => {
          this.restaurants = Array.isArray(data) ? data : [data];
          this.loading = false;
        },
        error: (error) => {
          console.error('Error loading restaurants:', error);
          this.loading = false;
          this.showErrorAlert('Error al cargar los restaurantes');
        }
      });
    } catch (error) {
      console.error('Error getting UID:', error);
      this.loading = false;
      this.showErrorAlert('Error al obtener la identificación del usuario');
    }
  }

  showCreateForm(): void {
    this.isEditMode = false;
    this.identifier = null;
    this.restaurantForm.reset();
    this.logoPreview = null;
    this.backgroundPreview = null;
    this.showForm = true;
  }

  editRestaurant(restaurant: any): void {
    this.isEditMode = true;
    this.identifier = restaurant.identifier;
    this.restaurantForm.patchValue(restaurant);
    this.logoPreview = restaurant.media?.logo;
    this.backgroundPreview = restaurant.media?.background;
    this.showForm = true;
  }

  onLogoSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.restaurantForm.patchValue({ logoFile: file });
      const reader = new FileReader();
      reader.onload = (e) => {
        this.logoPreview = e.target?.result as string;
      };
      reader.readAsDataURL(file);
    }
  }

  onBackgroundSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.restaurantForm.patchValue({ backgroundFile: file });
      const reader = new FileReader();
      reader.onload = (e) => {
        this.backgroundPreview = e.target?.result as string;
      };
      reader.readAsDataURL(file);
    }
  }

  cancelEdit(): void {
    Swal.fire({
      title: '¿Está seguro?',
      text: "Perderá todos los cambios realizados",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, cancelar',
      cancelButtonText: 'No, continuar editando'
    }).then((result) => {
      if (result.isConfirmed) {
        this.showForm = false;
        this.restaurantForm.reset();
      }
    });
  }

  async onSubmit(): Promise<void> {
    if (this.restaurantForm.valid) {
      const formData = this.restaurantForm.value;
      
      try {
        Swal.fire({
          title: 'Procesando...',
          text: 'Por favor espere',
          allowOutsideClick: false,
          didOpen: () => {
            Swal.showLoading();
          }
        });

        if (this.isEditMode && this.identifier) {
          this.restaurantService.updateRestaurant(this.identifier, formData).subscribe({
            next: () => {
              this.loadRestaurants();
              this.showForm = false;
              Swal.fire({
                title: '¡Éxito!',
                text: 'Restaurante actualizado correctamente',
                icon: 'success'
              });
            },
            error: (error) => {
              console.error('Error updating restaurant:', error);
              this.showErrorAlert('Error al actualizar el restaurante');
            }
          });
        } else {
          const logoFile = this.restaurantForm.get('logoFile')?.value;
          const backgroundFile = this.restaurantForm.get('backgroundFile')?.value;
          
          const observable = await this.restaurantService.createRestaurant(
            formData,
            logoFile,
            backgroundFile
          );
          
          observable.subscribe({
            next: () => {
              this.loadRestaurants();
              this.showForm = false;
              Swal.fire({
                title: '¡Éxito!',
                text: 'Restaurante creado correctamente',
                icon: 'success'
              });
            },
            error: (error) => {
              console.error('Error creating restaurant:', error);
              this.showErrorAlert('Error al crear el restaurante');
            }
          });
        }
      } catch (error) {
        console.error('Error processing form:', error);
        this.showErrorAlert('Error al procesar el formulario');
      }
    } else {
      Swal.fire({
        title: 'Error',
        text: 'Por favor complete todos los campos requeridos correctamente',
        icon: 'error'
      });
    }
  }

  deleteRestaurant(identifier: number): void {
    Swal.fire({
      title: '¿Está seguro?',
      text: "Esta acción desactivará el restaurante",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, desactivar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.restaurantService.deleteRestaurant(identifier).subscribe({
          next: () => {
            this.loadRestaurants();
            Swal.fire(
              '¡Desactivado!',
              'El restaurante ha sido desactivado correctamente',
              'success'
            );
          },
          error: (error) => {
            console.error('Error deleting restaurant:', error);
            this.showErrorAlert('Error al desactivar el restaurante');
          }
        });
      }
    });
  }

  restoreRestaurant(identifier: number): void {
    Swal.fire({
      title: '¿Está seguro?',
      text: "Esta acción reactivará el restaurante",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, reactivar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.restaurantService.restoreRestaurant(identifier).subscribe({
          next: () => {
            this.loadRestaurants();
            Swal.fire(
              '¡Reactivado!',
              'El restaurante ha sido reactivado correctamente',
              'success'
            );
          },
          error: (error) => {
            console.error('Error restoring restaurant:', error);
            this.showErrorAlert('Error al reactivar el restaurante');
          }
        });
      }
    });
  }

  private showErrorAlert(message: string): void {
    Swal.fire({
      title: 'Error',
      text: message,
      icon: 'error',
      confirmButtonText: 'Aceptar'
    });
  }
}