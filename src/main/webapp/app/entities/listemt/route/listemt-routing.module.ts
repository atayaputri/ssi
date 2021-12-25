import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LISTEMTComponent } from '../list/listemt.component';
import { LISTEMTDetailComponent } from '../detail/listemt-detail.component';
import { LISTEMTUpdateComponent } from '../update/listemt-update.component';
import { LISTEMTRoutingResolveService } from './listemt-routing-resolve.service';

const lISTEMTRoute: Routes = [
  {
    path: '',
    component: LISTEMTComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LISTEMTDetailComponent,
    resolve: {
      lISTEMT: LISTEMTRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LISTEMTUpdateComponent,
    resolve: {
      lISTEMT: LISTEMTRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LISTEMTUpdateComponent,
    resolve: {
      lISTEMT: LISTEMTRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(lISTEMTRoute)],
  exports: [RouterModule],
})
export class LISTEMTRoutingModule {}
