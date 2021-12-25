jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITBNEG, TBNEG } from '../tbneg.model';
import { TBNEGService } from '../service/tbneg.service';

import { TBNEGRoutingResolveService } from './tbneg-routing-resolve.service';

describe('TBNEG routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TBNEGRoutingResolveService;
  let service: TBNEGService;
  let resultTBNEG: ITBNEG | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(TBNEGRoutingResolveService);
    service = TestBed.inject(TBNEGService);
    resultTBNEG = undefined;
  });

  describe('resolve', () => {
    it('should return ITBNEG returned by find', () => {
      // GIVEN
      service.find = jest.fn(negcod => of(new HttpResponse({ body: { negcod } })));
      mockActivatedRouteSnapshot.params = { negcod: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBNEG = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultTBNEG).toEqual({ negcod: 'ABC' });
    });

    it('should return new ITBNEG if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBNEG = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTBNEG).toEqual(new TBNEG());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TBNEG })));
      mockActivatedRouteSnapshot.params = { negcod: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBNEG = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultTBNEG).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
