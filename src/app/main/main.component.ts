import { AuthService } from '../services/auth/authService';
import { ChangeDetectorRef, Component, enableProdMode, OnInit } from '@angular/core';
import jsPDF from 'jspdf';
import 'jspdf-autotable';
import { RestauranteService } from '../services/restaurant/restaurante.service';
import { Router } from '@angular/router';
import { take } from 'rxjs';
import { AppModule } from '../app.module';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

declare module 'jspdf' {
  interface jsPDF {
    autoTable: (columnas: string[], filas: any[], opciones?: any) => void;
  }
}

enableProdMode();

platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  userName: string = '';
  platoscarta: any[] = [];
  platosmenu: any[] = [];
  allPlatos: any[] = [];
  searchResults: any[] = [];
  showResultBox: boolean = false;
  limitedSearchResults: any[] = [];
  currentCartaPage: number = 1;
  currentMenuPage: number = 1;
  currentRestaurantesPage: number = 1;
  pageSize: number = 8;
  paginatedPlatosCarta: any[] = [];
  paginatedPlatosMenu: any[] = [];
  totalPagesCarta: number = 1;
  totalPagesMenu: number = 1;
  searchTerm: string = '';
  restaurantes: any[] = [];
  restauranteSeleccionado: any = null;
  p: number = 1;
  itemsPerPage: number = 3; // Ajustado a 3 restaurantes por página
  totalPagesRestaurantes: number = 1;

  constructor(
    private authService: AuthService, 
    private restauranteService: RestauranteService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) { }

  async ngOnInit(): Promise<void> {
    this.updatePage();
    this.initCarousel();
    this.listarRestaurantes();

    
  }
  verRestauranteDetalle(restaurante: any): void {
    // Navegar a la ruta de detalles del restaurante con el ID como parámetro
    this.router.navigate(['/detalles', restaurante.identificador]);}
    
  onRestaurantesPageChange(page: number) {
    this.currentRestaurantesPage = page;
  }

  verRestaurante(restaurante: any): void {
    this.restauranteSeleccionado = restaurante;
  }

  onCartaPageChange(page: number): void {
    if (page < 1 || page > this.totalPagesCarta) return;
    this.currentCartaPage = page;
    this.updatePaginatedPlatosCarta();
  }

  listarRestaurantes(): void {
    this.restauranteService.obtenerTodos().subscribe(
      (data: any[]) => {
        this.restaurantes = data;
        this.totalPagesRestaurantes = Math.ceil(this.restaurantes.length / this.itemsPerPage); // Actualizado aquí
      },
      (error: any) => {
        console.error('Error al obtener restaurantes', error);
      }
    );
  }

  onMenuPageChange(page: number): void {
    if (page < 1 || page > this.totalPagesMenu) return;
    this.currentMenuPage = page;
    this.updatePaginatedPlatosMenu();
  }

  updatePaginatedPlatosCarta(): void {
    const start = (this.currentCartaPage - 1) * this.pageSize;
    const end = start + this.pageSize;
    this.paginatedPlatosCarta = this.platoscarta.slice(start, end);
    this.totalPagesCarta = Math.ceil(this.platoscarta.length / this.pageSize);
  }

  updatePaginatedPlatosMenu(): void {
    const start = (this.currentMenuPage - 1) * this.pageSize;
    const end = start + this.pageSize;
    this.paginatedPlatosMenu = this.platosmenu.slice(start, end);
    this.totalPagesMenu = Math.ceil(this.platosmenu.length / this.pageSize);
  }

  getPaginationArray(totalPages: number): number[] {
    return Array.from({ length: totalPages }, (_, i) => i + 1);
  }

  searchPlatos(): void {
    this.searchResults = this.allPlatos.filter(plato => plato.nombre.toLowerCase().includes(this.searchTerm.toLowerCase()));
    this.limitedSearchResults = this.searchResults.slice(0, 5);
    this.showResultBox = this.searchTerm.trim() !== '' && this.searchResults.length > 0;
  }

  getPrecioTachado(precio: number): number {
    return precio + Math.floor(Math.random() * 3) + 2;
  }

  async updatePage(): Promise<void> {
    try {
      this.userName = await this.authService.getUserName();
    } catch (error) {
      console.error('Error getting user name', error);
      this.userName = '';
    }
  }

  initCarousel(): void {
    "use strict";

    const select = (el: string, all = false): HTMLElement | HTMLElement[] | null => {
      el = el.trim();
      if (all) {
        return Array.from(document.querySelectorAll(el)) as HTMLElement[];
      } else {
        return document.querySelector(el) as HTMLElement | null;
      }
    }

    let heroCarouselIndicators = select("#hero-carousel-indicators") as HTMLElement;
    let heroCarouselItems = select('#heroCarousel .carousel-item', true) as HTMLElement[];

    heroCarouselItems.forEach((item: HTMLElement, index: number) => {
      heroCarouselIndicators.innerHTML += `<li data-bs-target='#heroCarousel' data-bs-slide-to='${index}' class='${index === 0 ? 'active' : ''}'></li>`;
    });
  }

  
}
