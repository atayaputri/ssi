import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TBPARTService } from '../service/tbpart.service';

import { TBPARTComponent } from './tbpart.component';

describe('TBPART Management Component', () => {
  let comp: TBPARTComponent;
  let fixture: ComponentFixture<TBPARTComponent>;
  let service: TBPARTService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TBPARTComponent],
    })
      .overrideTemplate(TBPARTComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TBPARTComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TBPARTService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ tpacode: 'ABC' }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.tBPARTS?.[0]).toEqual(expect.objectContaining({ tpacode: 'ABC' }));
  });
});
