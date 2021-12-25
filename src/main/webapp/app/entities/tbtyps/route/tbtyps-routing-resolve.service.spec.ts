jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITBTYPS, TBTYPS } from '../tbtyps.model';
import { TBTYPSService } from '../service/tbtyps.service';

import { TBTYPSRoutingResolveService } from './tbtyps-routing-resolve.service';

describe('TBTYPS routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TBTYPSRoutingResolveService;
  let service: TBTYPSService;
  let resultTBTYPS: ITBTYPS | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(TBTYPSRoutingResolveService);
    service = TestBed.inject(TBTYPSService);
    resultTBTYPS = undefined;
  });

  describe('resolve', () => {
    it('should return ITBTYPS returned by find', () => {
      // GIVEN
      service.find = jest.fn(tpscod => of(new HttpResponse({ body: { tpscod } })));
      mockActivatedRouteSnapshot.params = { tpscod: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBTYPS = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultTBTYPS).toEqual({ tpscod: 'ABC' });
    });

    it('should return new ITBTYPS if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBTYPS = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTBTYPS).toEqual(new TBTYPS());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TBTYPS })));
      mockActivatedRouteSnapshot.params = { tpscod: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBTYPS = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultTBTYPS).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
