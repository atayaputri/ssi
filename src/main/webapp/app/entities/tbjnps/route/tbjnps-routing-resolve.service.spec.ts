jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITBJNPS, TBJNPS } from '../tbjnps.model';
import { TBJNPSService } from '../service/tbjnps.service';

import { TBJNPSRoutingResolveService } from './tbjnps-routing-resolve.service';

describe('TBJNPS routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TBJNPSRoutingResolveService;
  let service: TBJNPSService;
  let resultTBJNPS: ITBJNPS | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(TBJNPSRoutingResolveService);
    service = TestBed.inject(TBJNPSService);
    resultTBJNPS = undefined;
  });

  describe('resolve', () => {
    it('should return ITBJNPS returned by find', () => {
      // GIVEN
      service.find = jest.fn(jpscod => of(new HttpResponse({ body: { jpscod } })));
      mockActivatedRouteSnapshot.params = { jpscod: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBJNPS = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultTBJNPS).toEqual({ jpscod: 'ABC' });
    });

    it('should return new ITBJNPS if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBJNPS = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTBJNPS).toEqual(new TBJNPS());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TBJNPS })));
      mockActivatedRouteSnapshot.params = { jpscod: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBJNPS = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultTBJNPS).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
