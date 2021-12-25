jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITBBROKER, TBBROKER } from '../tbbroker.model';
import { TBBROKERService } from '../service/tbbroker.service';

import { TBBROKERRoutingResolveService } from './tbbroker-routing-resolve.service';

describe('TBBROKER routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TBBROKERRoutingResolveService;
  let service: TBBROKERService;
  let resultTBBROKER: ITBBROKER | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(TBBROKERRoutingResolveService);
    service = TestBed.inject(TBBROKERService);
    resultTBBROKER = undefined;
  });

  describe('resolve', () => {
    it('should return ITBBROKER returned by find', () => {
      // GIVEN
      service.find = jest.fn(brcode => of(new HttpResponse({ body: { brcode } })));
      mockActivatedRouteSnapshot.params = { brcode: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBBROKER = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultTBBROKER).toEqual({ brcode: 'ABC' });
    });

    it('should return new ITBBROKER if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBBROKER = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTBBROKER).toEqual(new TBBROKER());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TBBROKER })));
      mockActivatedRouteSnapshot.params = { brcode: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBBROKER = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultTBBROKER).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
