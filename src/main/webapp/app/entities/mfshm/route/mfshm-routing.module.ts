import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MFSHMComponent } from '../list/mfshm.component';
import { MFSHMDetailComponent } from '../detail/mfshm-detail.component';
import { MFSHMUpdateComponent } from '../update/mfshm-update.component';
import { MFSHMRoutingResolveService } from './mfshm-routing-resolve.service';

const mFSHMRoute: Routes = [
  {
    path: '',
    component: MFSHMComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MFSHMDetailComponent,
    resolve: {
      mFSHM: MFSHMRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MFSHMUpdateComponent,
    resolve: {
      mFSHM: MFSHMRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MFSHMUpdateComponent,
    resolve: {
      mFSHM: MFSHMRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(mFSHMRoute)],
  exports: [RouterModule],
})
export class MFSHMRoutingModule {}
