export class Ticket{
    id : string;
    key : string;
    summary : string;
    description : string;
    type : string;

    constructor(id: string, key: string, summary : string , description: string , type : string) {
        this.id = id;
        this.key = key;
        this.summary = summary;
        this.description = description;
        this.type = type;
    }
}