import { Component } from '@angular/core';
import { TicketService } from '../service/ticket.service';
import { Commit } from '../shared/commit';
import { Ticket } from '../shared/ticket';
import * as XLSX from 'xlsx';
import { saveAs } from 'file-saver';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import { ViewChild } from '@angular/core'
import { MatTableDataSource } from '@angular/material/table';
import { MinCommit } from '../shared/minCommit';
import { Papa } from 'ngx-papaparse';

@Component({
  selector: 'app-ticket',
  templateUrl: './ticket.component.html',
  styleUrls: ['./ticket.component.scss']
})
export class TicketComponent {
  btn : boolean = false;
  dataOk : boolean = false;
  dataSource = new MatTableDataSource<MinCommit>();
  displayedColumns: string[] = ['Project' , 'Repository' , 'CommitDate' , 'CommitHash' ,'Branch' , 'Description'];
  ticketIdsAsString : string = '';
  ticketIds : string[] = [];

  constructor(private ticketService: TicketService,private papa: Papa) {
  
  }

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }
  
  exportToExcel(): void {
    const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(this.dataSource.data);
    const workbook: XLSX.WorkBook = { Sheets: { 'data': worksheet }, SheetNames: ['data'] };

    const excelBuffer: any = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });

    const blob = new Blob([excelBuffer], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8' });
    saveAs(blob, 'table.xlsx');
  }

  getCommitsByTicketIds() {
    this.dataSource.data = [];
    this.ticketIds = this.ticketIdsAsString.split(',');
    this.dataOk = false;
    this.btn= true;
    this.ticketService.getCommitsByTicketIds(this.ticketIds).subscribe(
      (result: Commit[]) => {
        this.dataSource.data = result.map(commit => new MinCommit(commit.project,commit.repository, commit.date, commit.hash, commit.branch, commit.message));
        this.btn= false;
        this.dataOk = true;
      }
    );
  }

  handleFileInput(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        const contents = e.target.result;
        this.parseCSV(contents);
        this.dataSource.data = [];
        this.dataOk = false;
        this.btn= true;
        this.ticketService.getCommitsByTicketIds(this.ticketIds).subscribe(
      (result: Commit[]) => {
        this.dataSource.data = result.map(commit => new MinCommit(commit.project,commit.repository, commit.date, commit.hash, commit.branch, commit.message));
        this.btn= false;
        this.dataOk = true;
      }
    );
      };
      reader.readAsText(file);
    }

    
  }

  parseCSV(contents: string) {
    const lines = contents.split('\n');
    for (const line of lines) {
      const names = line.split(',');
      for (const name of names) {
        const trimmedName = name.trim();
        if (trimmedName) {
          this.ticketIds.push(trimmedName);
        }
      }
    }
  }
}
