import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TBPROVComponent } from '../list/tbprov.component';
import { TBPROVDetailComponent } from '../detail/tbprov-detail.component';
import { TBPROVUpdateComponent } from '../update/tbprov-update.component';
import { TBPROVRoutingResolveService } from './tbprov-routing-resolve.service';

const tBPROVRoute: Routes = [
  {
    path: '',
    component: TBPROVComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':provcod/view',
    component: TBPROVDetailComponent,
    resolve: {
      tBPROV: TBPROVRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TBPROVUpdateComponent,
    resolve: {
      tBPROV: TBPROVRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':provcod/edit',
    component: TBPROVUpdateComponent,
    resolve: {
      tBPROV: TBPROVRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tBPROVRoute)],
  exports: [RouterModule],
})
export class TBPROVRoutingModule {}
