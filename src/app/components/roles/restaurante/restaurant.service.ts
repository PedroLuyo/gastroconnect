import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../../../services/auth/authService';
import { CloudinaryService } from '../../../services/cloudinary/Cloudinary.service';

@Injectable({
  providedIn: 'root'
})
export class RestaurantService {
  private apiUrl = 'http://localhost:8082/api/restaurants';

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private cloudinaryService: CloudinaryService
  ) { }

  getRestaurantByUid(uid: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/obtain/uid/${uid}`);
  }

  async createRestaurant(restaurantData: any, logoFile: File, backgroundFile: File): Promise<Observable<any>> {
    const uid = await this.authService.getUserUid();
    
    // Upload images to Cloudinary
    const logoFormData = new FormData();
    logoFormData.append('file', logoFile);
    logoFormData.append('upload_preset', 'ml_default');
    
    const backgroundFormData = new FormData();
    backgroundFormData.append('file', backgroundFile);
    backgroundFormData.append('upload_preset', 'ml_default');

    const logoResponse = await this.cloudinaryService.uploadImg(logoFormData).toPromise();
    const backgroundResponse = await this.cloudinaryService.uploadImg(backgroundFormData).toPromise();

    // Prepare restaurant data
    const restaurant = {
      ...restaurantData,
      uid,
      media: {
        logo: logoResponse.secure_url,
        background: backgroundResponse.secure_url
      }
    };

    return this.http.post(`${this.apiUrl}/create`, [restaurant]);
  }

  updateRestaurant(identifier: number, restaurantData: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/edit/${identifier}`, restaurantData);
  }

  deleteRestaurant(identifier: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/delete/${identifier}`, {});
  }

  restoreRestaurant(identifier: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/restore/${identifier}`, {});
  }
}