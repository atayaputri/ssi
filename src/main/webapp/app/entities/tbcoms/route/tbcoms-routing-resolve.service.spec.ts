jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITBCOMS, TBCOMS } from '../tbcoms.model';
import { TBCOMSService } from '../service/tbcoms.service';

import { TBCOMSRoutingResolveService } from './tbcoms-routing-resolve.service';

describe('TBCOMS routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TBCOMSRoutingResolveService;
  let service: TBCOMSService;
  let resultTBCOMS: ITBCOMS | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(TBCOMSRoutingResolveService);
    service = TestBed.inject(TBCOMSService);
    resultTBCOMS = undefined;
  });

  describe('resolve', () => {
    it('should return ITBCOMS returned by find', () => {
      // GIVEN
      service.find = jest.fn(cocode => of(new HttpResponse({ body: { cocode } })));
      mockActivatedRouteSnapshot.params = { cocode: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBCOMS = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultTBCOMS).toEqual({ cocode: 'ABC' });
    });

    it('should return new ITBCOMS if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBCOMS = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTBCOMS).toEqual(new TBCOMS());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TBCOMS })));
      mockActivatedRouteSnapshot.params = { cocode: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBCOMS = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultTBCOMS).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
