import { Injectable } from '@angular/core';

import { Ticket } from '../shared/ticket';
import { Commit } from '../shared/commit';

import { Observable } from 'rxjs';

import {tap} from 'rxjs/operators';

import { HttpClient } from '@angular/common/http';
import { baseURL } from '../shared/baseURL';
import { Repository } from '../shared/repository';

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  constructor(private http: HttpClient) { }

  
  getTickets(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(baseURL + 'tickets/get')
      .pipe(
        tap()
      );
  }

  getTicketsByCommitId(id : string): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(baseURL + 'tickets/getTicketsByCommitId?commitId=' + id)
      .pipe(
        tap(
          
        )
      );
  }

  getProjects(): Observable<string[]> {
    return this.http.get<string[]>(baseURL + 'repository/getProjects')
      .pipe(
        tap(
          
        )
      );
  }

  getRepositories(project : string): Observable<Repository[]> {
    return this.http.get<Repository[]>(baseURL + 'repository/getRepositories?project='+project)
      .pipe(
        tap(
          
        )
      );
  }

  getTags(project : string , repository : string , branch : string):Observable<string[]>{
    return this.http.get<string[]>(baseURL + 'repository/getTags?project='+project+'&repository='+repository+'&branch='+ branch)
    .pipe(
      tap(
        
      )
    )
  }

  getCommitsByTag(project : string ,repository : string ,branch : string, tag : string): Observable<Commit[]> {
    return this.http.get<Commit[]>(baseURL + 'commits/getCommitsByTagId?project='+project+'&repository='+repository+'&branch='+branch+'&tagId='+tag)
    .pipe(
      tap(
        
      )
    )
  }

  getCommitsBetweenTags(project : string ,repository : string, branch : string  ,tag1 : string , tag2 : string): Observable<Commit[]> {
    return this.http.get<Commit[]>(baseURL + 'commits/getCommitsBetweenTags?project='+project+'&repository='+repository+'&branch='+branch +'&tag1='+tag1+'&tag2='+tag2)
    .pipe(
      tap(
        
      )
    )
  }

  getAllCommits(project : string , repository : string , branch : string): Observable<Commit[]> {
    return this.http.get<Commit[]>(baseURL + 'commits/getAllCommits?project='+project+'&repository='+repository+'&branch='+branch)
    .pipe(
      tap(
        
      )
    )
  }

  getBranches(repository : string):Observable<string[]>{
    return this.http.get<string[]>(baseURL + 'repository/getBranches?repository='+repository)
    .pipe(
      tap(
        
      )
    )
  }

  getCommitsByTicketIds(ticketIds : string[]):Observable<Commit[]>{
    return this.http.post<Commit[]>(baseURL + 'repository/getCommitsByTicketIds',ticketIds)
    .pipe(
      tap(
        
      )
    )
  }

  
}
