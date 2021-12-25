jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITBPART, TBPART } from '../tbpart.model';
import { TBPARTService } from '../service/tbpart.service';

import { TBPARTRoutingResolveService } from './tbpart-routing-resolve.service';

describe('TBPART routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TBPARTRoutingResolveService;
  let service: TBPARTService;
  let resultTBPART: ITBPART | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(TBPARTRoutingResolveService);
    service = TestBed.inject(TBPARTService);
    resultTBPART = undefined;
  });

  describe('resolve', () => {
    it('should return ITBPART returned by find', () => {
      // GIVEN
      service.find = jest.fn(tpacode => of(new HttpResponse({ body: { tpacode } })));
      mockActivatedRouteSnapshot.params = { tpacode: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBPART = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultTBPART).toEqual({ tpacode: 'ABC' });
    });

    it('should return new ITBPART if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBPART = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTBPART).toEqual(new TBPART());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TBPART })));
      mockActivatedRouteSnapshot.params = { tpacode: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBPART = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultTBPART).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
