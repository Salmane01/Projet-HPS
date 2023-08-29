import { Component } from '@angular/core';
import { LoginService } from '../service/login.service';
import { Credentials } from '../shared/credentials';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  credentials : Credentials = new Credentials('','','','','','');

  constructor(private loginService : LoginService, private router: Router){}

  submit(){
    this.loginService.submitCredentials(this.credentials).subscribe(
      (result : Credentials) => {
        this.router.navigateByUrl('/commit');
      }
    );
  }
}
