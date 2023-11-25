import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserAuthentication} from "../../models/user/UserAuthentication";
import {Token} from "../../models/user/Token";
import {environment} from "../../../environment/environment";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) { }

  public login(userLogin: UserAuthentication) {
    const body = JSON.stringify(userLogin);
    return this.http.post<Token>(environment.backendURL + "/auth/login", body, {
      headers: {
        "Content-Type": "application/json"
      }
    })
  }
}
