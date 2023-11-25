import { Component } from '@angular/core';
import {environment} from "../../../environment/environment";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  constructor(private router: Router) {}

  goToRegisterPage() {
    this.router.navigate(["/register"]);
  }

  goToLoginPage() {
    this.router.navigate(["/login"]);
  }
}
