import { Component } from '@angular/core';
import {UserAuthentication} from "../../models/user/UserAuthentication";
import {LoginService} from "../../services/authentication/login.service";
import {Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css', '../register/register.component.css']
})
export class LoginComponent {
  userLogin: UserAuthentication = new UserAuthentication();

  constructor(private loginService: LoginService,
              private router: Router,
              private cookieService: CookieService) {
  }

  public login() {
    this.loginService.login(this.userLogin).subscribe({
      next: (token) => {
        this.cookieService.set("jwt-token", token.token);
        this.router.navigate(["/main"]);
      },
      error: (error) => {
        console.log(error);
      }
    })
  }
}
