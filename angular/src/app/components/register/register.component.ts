import { Component } from '@angular/core';
import {RegisterService} from "../../services/authentication/register.service";
import {Router} from "@angular/router";
import {UserAuthentication} from "../../models/user/UserAuthentication";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerUser: UserAuthentication = new UserAuthentication();

  constructor(private registerService: RegisterService,
              private router: Router) {}

  public register() {
    if (!this.isValidUser(this.registerUser)) {
      console.log("invalid user data");
      return;
    }

    this.registerService.register(this.registerUser).subscribe({
      next: (user) => {
        this.registerUser = new UserAuthentication();
        this.router.navigate(["/login"]);
      },
      error: (error) => {
        console.log(error);
      }
    })
  }

  private isValidUser(user: UserAuthentication): boolean {
    return (user.username.length > 5 && user.password.length > 5);
  }
}
