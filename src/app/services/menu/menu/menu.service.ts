// menu.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Menu } from '../../../models/menu/menu/menu';
import { map } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class MenuService { 
  private apiUrl = 'http://localhost:8081/api/menus';

  constructor(private http: HttpClient) {}

  getAllMenu(): Observable<Menu[]> {
    return this.http.get<Menu[]>(`${this.apiUrl}/obtain`).pipe(
      map(comidas => comidas.sort((a, b) => b.menuid - a.menuid))
    );
  }

  getMenusByEstado(estado: string): Observable<Menu[]> {
    return this.http.get<Menu[]>(`${this.apiUrl}/obtain/${estado}`).pipe(
      map(comidas => comidas.sort((a, b) => b.menuid - a.menuid))
    );
  }

  getMenusLocalStorage(): Menu[] {
    const menusStr = localStorage.getItem('menus');
    return menusStr ? JSON.parse(menusStr) : [];
  }

  setMenusLocalStorage(menus: Menu[]): void {
    localStorage.setItem('menus', JSON.stringify(menus));
  }

  eliminarMenu(id: number): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/eliminar/${id}`, {});
  }

  restaurarMenu(id: number): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/restaurar/${id}`, {});
  }

  editarMenu(id: number, menu: Menu): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/editar/${id}`, menu);
  }

  crearMenu(menu: Menu): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/insertar`, menu);
  }
}
