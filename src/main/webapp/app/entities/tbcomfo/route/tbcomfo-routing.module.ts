import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TBCOMFOComponent } from '../list/tbcomfo.component';
import { TBCOMFODetailComponent } from '../detail/tbcomfo-detail.component';
import { TBCOMFOUpdateComponent } from '../update/tbcomfo-update.component';
import { TBCOMFORoutingResolveService } from './tbcomfo-routing-resolve.service';

const tBCOMFORoute: Routes = [
  {
    path: '',
    component: TBCOMFOComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TBCOMFODetailComponent,
    resolve: {
      tBCOMFO: TBCOMFORoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TBCOMFOUpdateComponent,
    resolve: {
      tBCOMFO: TBCOMFORoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TBCOMFOUpdateComponent,
    resolve: {
      tBCOMFO: TBCOMFORoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tBCOMFORoute)],
  exports: [RouterModule],
})
export class TBCOMFORoutingModule {}
