import {HttpClient} from '@angular/common/http';
import {inject, Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AoCSolution} from '../mdoule/aoc-solution.module';
import {environment} from '../../environment/environment';
import {Years} from '../mdoule/year.module';

@Injectable({providedIn: 'root'})
export class SerializationService {
  private readonly apiServerUrl = environment.baseUrl;

  private readonly http = inject(HttpClient);

  public availability$(): Observable<Years> {
    return this.http.get<Years>(this.apiServerUrl + 'availability');
  }

  public uploadFile$(year: number, day: number, formData: FormData): Observable<AoCSolution> {
    return this.http.post<AoCSolution>(this.apiServerUrl + `upload/${year}/${day}`, formData);
  }
}
