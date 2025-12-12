import {inject, Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {HeaderService} from "./header.service";
import {environment} from "../environment/environment"

@Injectable({providedIn: 'root'})
export class UploadFileService {
  private apiServerUrl = environment.baseUrl + 'file/';

  private http = inject(HttpClient);
  private headerService = inject(HeaderService);

  public uploadFile(formData: FormData): Observable<any> {
    return this.http.post<any>(this.apiServerUrl + 'upload', formData, {
      headers: this.headerService.getHeader(),
    });
  }
}
