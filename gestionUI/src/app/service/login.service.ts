import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Credentials } from '../shared/credentials';
import { baseURL } from '../shared/baseURL';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {


  credentials: Credentials;

  constructor(private http: HttpClient) { 
     this.credentials= new Credentials('', '', '', '' ,'' ,'');
  }

  isAuthenticated() {
    return (this.credentials.jiraURL&&this.credentials.jiraUsername&&this.credentials.jiraPassword&&this.credentials.bitbucketURL&&this.credentials.bitbucketUsername&&this.credentials.bitbucketPassword);
  }

  submitCredentials(creds : Credentials):Observable<Credentials>{
    this.credentials = creds;
    return this.http.post<Credentials>(baseURL + 'login/updateCredentials',this.credentials)
    .pipe(
      tap()
    )
  }

}
