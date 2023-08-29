import { Ticket } from "./ticket";

export class Commit{
    hash : string;
    message : string;
    date : string;
    author : string;
    repository : string;
    project : string;
    branch : string;
    jiraID : string;
    type : string;
    issueID : string;
    summary : string;

    constructor(hash: string, message: string, date: string , author : string, repository : string, project : string ,branch : string , jiraID : string, type : string, issueID : string , summary : string) {
        this.hash = hash;
        this.message = message;
        this.date = date;
        this.author = author;
        this.repository = repository;
        this.project = project;
        this.branch = branch;
        this.jiraID = jiraID;
        this.type = type;
        this.issueID = issueID;
        this.summary = summary;

    }
}