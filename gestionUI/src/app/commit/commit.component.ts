import { Component } from '@angular/core';
import { Ticket } from '../shared/ticket';
import { Commit } from '../shared/commit';
import { TicketService } from '../service/ticket.service';
import * as XLSX from 'xlsx';
import { saveAs } from 'file-saver';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import { ViewChild } from '@angular/core'
import { MatTableDataSource } from '@angular/material/table';
import { Repository } from '../shared/repository';

@Component({
  selector: 'app-commit',
  templateUrl: './commit.component.html',
  styleUrls: ['./commit.component.scss']
})

export class CommitComponent {
  tags : string[];
  projects : string[];
  repositories : Repository[];
  branches : string[];
  tickets: Ticket[];
  ticketsByCommit : Ticket[];
  commitId : string = '';
  btn : boolean = false;
  dataOk : boolean = false;
  btnClicked: boolean = false;
  btn2Clicked: boolean = false;
  project : string = '';
  repository : string = '';
  branch : string = '';
  tag1 : string = '';
  tag2 : string = '';
  dataSource = new MatTableDataSource<Commit>();
  displayedColumns: string[] = ['Author' , 'CommitDate' , 'CommitHash' , 'CommitMessage' , 'jiraID' , 'type' , 'issueID'  , 'summary'];

  constructor(private ticketService: TicketService) {
    this.tickets = [];
    this.ticketsByCommit = [];
    this.repositories = [];
    this.tags = [];
    this.branches = [];
    this.projects = [];
  }

  ngOnInit() {
    this.getProjects();
  }

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  getTickets(){
    this.btnClicked = true;
    this.ticketService.getTickets().subscribe(tickets => {
      this.tickets = tickets;
      this.btnClicked = false;
    })
  }

  getTicketsByCommitId(){
    this.btn2Clicked = true;
    this.ticketService.getTicketsByCommitId(this.commitId).subscribe(tickets => {
      this.ticketsByCommit = tickets;
      this.btn2Clicked = false;
    })
  }

  getCommitsBetweenTags(){
    this.dataSource.data = [];
    this.dataOk = false;
    this.btn= true;
    if (this.tag1 == '' && this.tag2 == ''){
      this.ticketService.getAllCommits(this.project , this.repository , this.branch).subscribe(result => {
        this.dataSource.data = result;
        this.btn= false;
        this.dataOk = true;
      })
    }else if (this.tag2 == ''){
      this.ticketService.getCommitsByTag(this.project , this.repository , this.branch ,this.tag1).subscribe(result => {
        this.dataSource.data = result;
        this.btn= false;
        this.dataOk = true;
      })
    }else{
      this.ticketService.getCommitsBetweenTags(this.project , this.repository ,this.branch , this.tag1 , this.tag2).subscribe(result =>{
        this.dataSource.data = result;
        this.btn= false;
        this.dataOk = true;
      })
    }
    
  }

  getTags(repository : string , branch : string){
    this.tags = [];
    this.showProgress();
    this.ticketService.getTags(this.project , repository , branch).subscribe(tags=>{
      this.tags = tags;
      this.hideProgress();
    })
  }

  getProjects(){
    this.showProgress();
    this.ticketService.getProjects().subscribe(projects =>{
      this.projects =  projects;
      this.hideProgress();
    })
  }

  getRepositories(project : string){
    this.tags = [];
    this.branches = [];
    this.repositories = [];
    this.showProgress();
    this.ticketService.getRepositories(project).subscribe(repositories =>{
      this.repositories = repositories;
      this.hideProgress();
    })
  }

  getBranches(repository : string){
    this.tags = [];
    this.branches = [];
    this.showProgress();
    this.ticketService.getBranches(repository).subscribe(branches=>{
      this.branches = branches;
      this.hideProgress();
    })
  }

  onValueChangedProject(event : any){
    this.project = event.value;
    this.getRepositories(this.project);
  }

  onValueChangedRepository(event: any) {
    this.repository = event.value;
    this.getBranches(this.repository);
  }

  onValueChangedBranch(event : any){
    this.branch = event.value;
    this.getTags(this.repository , this.branch);
  }

  onValueChangedTag1(event : any){
    this.tag1 = event.value;
  }

  onValueChangedTag2(event : any){
    this.tag2 = event.value;
  }

  exportToExcel(): void {
    const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(this.dataSource.data);
    const workbook: XLSX.WorkBook = { Sheets: { 'data': worksheet }, SheetNames: ['data'] };

    const excelBuffer: any = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });

    const blob = new Blob([excelBuffer], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8' });
    saveAs(blob, 'table.xlsx');
  }

  showProgress(){
    const element = document.getElementById('progress');
    if (element) {
      element.classList.remove('custom-opacity');
    }
  }

  hideProgress(){
    const element = document.getElementById('progress');
    if (element) {
      element.classList.add('custom-opacity');
    }
  }

}
