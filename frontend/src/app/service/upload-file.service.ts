import {HttpClient} from '@angular/common/http';
import {inject, Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AoCSolution} from '../mdoule/aoc-solution.module';
import {environment} from '../../environment/environment';

@Injectable({providedIn: 'root'})
export class UploadFileService {
  private readonly apiServerUrl = environment.baseUrl;

  private readonly http = inject(HttpClient);

  public availability$(): Observable<any> {
    return this.http.get<AoCSolution[]>(this.apiServerUrl + 'availability');
  }

  public uploadFile$(year: number, day: number, formData: FormData): Observable<AoCSolution> {
    return this.http.post<AoCSolution>(this.apiServerUrl + `upload/${year}/${day}`, formData);
  }
}
