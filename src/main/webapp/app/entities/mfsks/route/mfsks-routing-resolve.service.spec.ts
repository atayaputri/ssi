jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IMFSKS, MFSKS } from '../mfsks.model';
import { MFSKSService } from '../service/mfsks.service';

import { MFSKSRoutingResolveService } from './mfsks-routing-resolve.service';

describe('MFSKS routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: MFSKSRoutingResolveService;
  let service: MFSKSService;
  let resultMFSKS: IMFSKS | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(MFSKSRoutingResolveService);
    service = TestBed.inject(MFSKSService);
    resultMFSKS = undefined;
  });

  describe('resolve', () => {
    it('should return IMFSKS returned by find', () => {
      // GIVEN
      service.find = jest.fn(skno => of(new HttpResponse({ body: { skno } })));
      mockActivatedRouteSnapshot.params = { skno: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMFSKS = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMFSKS).toEqual({ skno: 123 });
    });

    it('should return new IMFSKS if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMFSKS = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultMFSKS).toEqual(new MFSKS());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as MFSKS })));
      mockActivatedRouteSnapshot.params = { skno: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMFSKS = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMFSKS).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
