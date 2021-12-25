import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TBPARTComponent } from '../list/tbpart.component';
import { TBPARTDetailComponent } from '../detail/tbpart-detail.component';
import { TBPARTUpdateComponent } from '../update/tbpart-update.component';
import { TBPARTRoutingResolveService } from './tbpart-routing-resolve.service';

const tBPARTRoute: Routes = [
  {
    path: '',
    component: TBPARTComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':tpacode/view',
    component: TBPARTDetailComponent,
    resolve: {
      tBPART: TBPARTRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TBPARTUpdateComponent,
    resolve: {
      tBPART: TBPARTRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':tpacode/edit',
    component: TBPARTUpdateComponent,
    resolve: {
      tBPART: TBPARTRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tBPARTRoute)],
  exports: [RouterModule],
})
export class TBPARTRoutingModule {}
