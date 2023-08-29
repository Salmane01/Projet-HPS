export class Credentials{
    jiraURL : string;
    jiraUsername : string;
    jiraPassword : string;
    bitbucketURL : string;
    bitbucketUsername : string;
    bitbucketPassword : string;
    constructor(jiraURL : string ,jiraUsername : string , jiraPassword : string ,bitbucketURL : string, bitbucketUsername : string , bitbucketPassword : string){
        this.jiraURL = jiraURL;
        this.jiraUsername = jiraUsername;
        this.jiraPassword = jiraPassword;
        this.bitbucketURL = bitbucketURL;
        this.bitbucketUsername =bitbucketUsername;
        this.bitbucketPassword = bitbucketPassword;
    }
}