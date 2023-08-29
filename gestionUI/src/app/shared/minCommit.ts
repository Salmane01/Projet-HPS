export class MinCommit{
    project : string;
    repository: string;
    commitDate: string;
    commitHash: string;
    branch: string;
    description: string;

    constructor(project : string ,repository: string, commitDate: string, commitHash: string, branch: string, description: string) {
        this.project = project;
        this.repository = repository;
        this.commitDate = commitDate;
        this.commitHash = commitHash;
        this.branch = branch;
        this.description = description;
    }
}