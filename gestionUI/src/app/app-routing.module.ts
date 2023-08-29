import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CommitComponent } from './commit/commit.component';
import { TicketComponent } from './ticket/ticket.component';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './guard/auth.guard';

const routes: Routes = [
  { path: 'commit', component: CommitComponent, canActivate: [AuthGuard] },
  { path: 'ticket', component: TicketComponent, canActivate: [AuthGuard] },
  { path: '**', component: LoginComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
