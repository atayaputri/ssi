jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IMAPSKS, MAPSKS } from '../mapsks.model';
import { MAPSKSService } from '../service/mapsks.service';

import { MAPSKSRoutingResolveService } from './mapsks-routing-resolve.service';

describe('MAPSKS routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: MAPSKSRoutingResolveService;
  let service: MAPSKSService;
  let resultMAPSKS: IMAPSKS | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(MAPSKSRoutingResolveService);
    service = TestBed.inject(MAPSKSService);
    resultMAPSKS = undefined;
  });

  describe('resolve', () => {
    it('should return IMAPSKS returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMAPSKS = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMAPSKS).toEqual({ id: 123 });
    });

    it('should return new IMAPSKS if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMAPSKS = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultMAPSKS).toEqual(new MAPSKS());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as MAPSKS })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMAPSKS = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMAPSKS).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
