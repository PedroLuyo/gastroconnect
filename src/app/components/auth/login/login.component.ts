import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/auth/authService';
import { AppComponent } from '../../../app.component';
import { ToastrService } from 'ngx-toastr';
import { AngularFireAuth } from '@angular/fire/compat/auth';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  formLogin: FormGroup;

  constructor(
    private appComponent: AppComponent,
    private authService: AuthService,
    private router: Router,
    private toastr: ToastrService,
    private afAuth: AngularFireAuth
  ) {
    this.formLogin = new FormGroup({
      email: new FormControl(),
      name: new FormControl(),
      password: new FormControl(),
    });
    this.appComponent.showMenu = false;
  }

  ngOnDestroy() {
    this.appComponent.showMenu = true;
  }
  ngOnInit(): void { }

  async onSubmit() {
    try {
      await this.authService.login(this.formLogin.value);
      console.log('Usuario logueado, datos:');
      const userName = await this.authService.getUserName();
      this.toastr.success('Inicio de sesión exitoso', 'Correcto');
      console.log('Inicio de sesión exitoso');
      console.log(userName);
      this.router.navigate(['/main']);
    } catch (error: any) {
      if (error.code === 'auth/user-not-found') {
        this.toastr.error('No existe un usuario con ese correo electrónico', 'Error');
      } else if (error.code === 'auth/wrong-password') {
        this.toastr.error('Contraseña incorrecta', 'Error');
      } else if (error.code === 'auth/user-disabled') {
        this.toastr.error('El usuario ha sido deshabilitado', 'Error');
      } else {
        this.toastr.error('Error al iniciar sesión', 'Error');
      }
    }
  }

  async onClick() {
    try {
      await this.authService.loginWithGoogle();
      console.log('Usuario logueado con Google');
      this.router.navigate(['/main']);
    } catch (error) {
      console.log('Error al iniciar sesion con Google', error);
    }
  }

  scrollToTop(): void {
    window.scrollTo({
      top: 0,
      behavior: 'smooth'
    });
  }

  onSignInSuccess(event: any) {
    const user = event.authResult.user;
    const email = user.email;
    const name = user.displayName;

    this.authService
      .create({
        email: email,
        name: name,
        role: 'c',
        password: '',
        // direccion: 'direccion',
        dni: 'dni',
        estado: 'estado',
        // ruc: 'ruc'
      })
      .then(() => {
        this.toastr.success('Usuario registrado', 'Usuario registrado con éxito en Firestore');
        console.log('Usuario registrado con éxito en Firestore');
        this.router.navigate(['/main']);
      })
      .catch((error) => {
        this.toastr.error('Error al registrar', 'Error al registrar el usuario en Firestore');
        console.log('Error al registrar el usuario en Firestore', error);
      });
  }
  //validaciones del codigo, la logica se encuentra en la carpeta services por las buenas practicas
  registerUser(form: NgForm) {
    const value = form.value;
    const email = value.email;
    const name = value.name;
    const password = value.password;
    if (password.length < 6) {
      this.toastr.error('La contraseña debe tener al menos 6 caracteres', 'Error');
      return;
    }
    this.authService
      .register({
        email: email,
        name: name,
        password: password,
        role: 'comensal',
      })
      .then(() => {
        console.log('Usuario registrado con éxito');
        this.toastr.success('Usuario registrado con éxito', 'Correcto');
        this.router.navigate(['/main']);
      })
      .catch((error) => {
        console.log('Error al registrar el usuario', error);
        if (error.code === 'auth/email-already-in-use') {
          this.toastr.error('El correo electrónico ya está en uso', 'Error');
        } else if (error.code === 'auth/invalid-email') {
          this.toastr.error('El correo electrónico es inválido', 'Error');
        } else if (error.code === 'auth/weak-password') {
          this.toastr.error('La contraseña es demasiado débil', 'Error');
        } else {
          this.toastr.error('Error al registrar el usuario', 'Error');
        }
      });
  }

  showSuccess() {
    this.toastr.success('everything is broken', 'Major Error');
  }

  async sendPasswordResetEmail(email: string): Promise<void> {
    // Validación de correo vacío
    if (!email || email.trim() === '') {
      this.toastr.error('Por favor ingresa un correo electrónico', 'Error');
      return;
    }

    // Validación de formato de correo usando regex
    const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    if (!emailRegex.test(email)) {
      this.toastr.error('Por favor ingresa un correo electrónico válido', 'Error');
      return;
    }

    try {
      // Intenta enviar el correo de restablecimiento usando Firebase Auth
      await this.afAuth.sendPasswordResetEmail(email);
      
      this.toastr.success(
        'Si el correo existe en nuestro sistema, recibirás un enlace para restablecer tu contraseña', 
        'Correo enviado'
      );
    } catch (error: any) {
      console.error('Error al enviar el correo de restablecimiento:', error);
      
      let errorMessage = 'Error al enviar el correo de restablecimiento. Por favor, intenta de nuevo.';
      
      switch (error.code) {
        case 'auth/invalid-email':
          errorMessage = 'El formato del correo electrónico no es válido.';
          break;
        case 'auth/user-not-found':
          // Por seguridad, no revelamos si el usuario existe o no
          errorMessage = 'Si el correo existe en nuestro sistema, recibirás un enlace para restablecer tu contraseña.';
          break;
        case 'auth/too-many-requests':
          errorMessage = 'Has realizado demasiados intentos. Por favor, espera unos minutos.';
          break;
        case 'auth/network-request-failed':
          errorMessage = 'Error de conexión. Verifica tu conexión a internet.';
          break;
      }
      
      // Si es un error de usuario no encontrado, mostramos un success en lugar de error
      // para no revelar si el usuario existe o no (mejor seguridad)
      if (error.code === 'auth/user-not-found') {
        this.toastr.success(errorMessage, 'Solicitud procesada');
      } else {
        this.toastr.error(errorMessage, 'Error');
      }
    }
  }
  navigateToLoginBlock(): void {
    const loginBlockElement = document.getElementById('login-block');
    if (loginBlockElement) {
      loginBlockElement.scrollIntoView({ behavior: 'smooth' });
    }
  }
}
