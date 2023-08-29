import { Component } from '@angular/core';
import { LoginService } from './service/login.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})


export class AppComponent {
  title = 'projet HPS';

  constructor(private loginService: LoginService) {

  }

  isAuthenticated() {
    return this.loginService.isAuthenticated();
  }
}
