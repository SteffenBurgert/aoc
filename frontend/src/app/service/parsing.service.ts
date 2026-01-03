import {HttpClient} from '@angular/common/http';
import {inject, Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AoCSolution} from '../module/aoc-solution.module';
import {environment} from '../../environment/environment';
import {Years} from '../module/year.module';
import {Language} from '../module/language.module';
import {ImplementationResultModule} from '../module/implementation-result-module';

@Injectable({providedIn: 'root'})
export class ParsingService {
  private readonly baseUrl = environment.baseUrl;

  private readonly http = inject(HttpClient);

  public availability$(): Observable<Years> {
    return this.http.get<Years>(this.baseUrl + 'availability');
  }

  public uploadFile$(year: number, day: number, formData: FormData): Observable<AoCSolution> {
    return this.http.post<AoCSolution>(this.baseUrl + `upload/${year}/${day}`, formData);
  }

  public getImplementation$(language: Language, year: number, day: number): Observable<ImplementationResultModule> {
    return this.http.get<ImplementationResultModule>(this.baseUrl + `implementation/${Language[language]}/${year}/${day}`);
  }

}
