import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../../models/user/User";
import {environment} from "../../../environment/environment";
import {UserAuthentication} from "../../models/user/UserAuthentication";

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private http: HttpClient) { }

  public register(userRegister: UserAuthentication) {
    const body = JSON.stringify(userRegister);
    return this.http.post<User>(environment.backendURL + "/auth/register", body, {
      headers: {
        "Content-Type": "application/json"
      }});
  }
}
