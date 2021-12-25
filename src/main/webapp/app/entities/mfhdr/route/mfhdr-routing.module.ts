import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MFHDRComponent } from '../list/mfhdr.component';
import { MFHDRDetailComponent } from '../detail/mfhdr-detail.component';
import { MFHDRUpdateComponent } from '../update/mfhdr-update.component';
import { MFHDRRoutingResolveService } from './mfhdr-routing-resolve.service';

const mFHDRRoute: Routes = [
  {
    path: '',
    component: MFHDRComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':hdno/view',
    component: MFHDRDetailComponent,
    resolve: {
      mFHDR: MFHDRRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MFHDRUpdateComponent,
    resolve: {
      mFHDR: MFHDRRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':hdno/edit',
    component: MFHDRUpdateComponent,
    resolve: {
      mFHDR: MFHDRRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(mFHDRRoute)],
  exports: [RouterModule],
})
export class MFHDRRoutingModule {}
