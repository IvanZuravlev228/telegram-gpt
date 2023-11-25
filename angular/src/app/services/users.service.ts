import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../models/user/User";
import {environment} from "../../environment/environment";
import {CookieService} from "ngx-cookie-service";

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor(private http: HttpClient,
              private cookieService: CookieService) { }

  public getAllUsers() {
    return this.http.get<User[]>(environment.backendURL + "/users", {
      headers: {
        "Authorization": "Bearer " + this.cookieService.get("jwt-token")
      }});
  }

  public deleteUser(userId: number) {
    return this.http.delete<void>(environment.backendURL + "/users/" + userId, {
      headers: {
        "Authorization": "Bearer " + this.cookieService.get("jwt-token")
      }});
  }
}
