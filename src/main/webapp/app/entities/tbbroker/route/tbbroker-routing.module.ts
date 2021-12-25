import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TBBROKERComponent } from '../list/tbbroker.component';
import { TBBROKERDetailComponent } from '../detail/tbbroker-detail.component';
import { TBBROKERUpdateComponent } from '../update/tbbroker-update.component';
import { TBBROKERRoutingResolveService } from './tbbroker-routing-resolve.service';

const tBBROKERRoute: Routes = [
  {
    path: '',
    component: TBBROKERComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':brcode/view',
    component: TBBROKERDetailComponent,
    resolve: {
      tBBROKER: TBBROKERRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TBBROKERUpdateComponent,
    resolve: {
      tBBROKER: TBBROKERRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':brcode/edit',
    component: TBBROKERUpdateComponent,
    resolve: {
      tBBROKER: TBBROKERRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tBBROKERRoute)],
  exports: [RouterModule],
})
export class TBBROKERRoutingModule {}
