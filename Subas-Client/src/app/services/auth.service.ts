import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { RegisterPayload } from '../components/register-payload';
import { Observable } from 'rxjs';
import { LoginPayload } from '../components/login-payload';
import {JwtAutResponse} from '../components/jwt-aut-response';
import {LocalStorageService} from 'ngx-webstorage';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  

  private url = 'http://localhost:8080/api/auth/';

  constructor(private httpClient: HttpClient,  private localStoraqeService: LocalStorageService) { 
  }

  register(registerPayload: RegisterPayload): Observable<any> {
    return this.httpClient.post(this.url + 'signup', registerPayload);
  }

  login(loginPayload: LoginPayload): Observable<boolean> {
    
    return this.httpClient.post<JwtAutResponse>(this.url + 'login', loginPayload).pipe(map(data => {
      console.log("a");
      this.localStoraqeService.store('authenticationToken', data.authenticationToken);
      this.localStoraqeService.store('username', data.username);
      return true;
    }));
  }

}

