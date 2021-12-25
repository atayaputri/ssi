import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TABFEEService } from '../service/tabfee.service';

import { TABFEEComponent } from './tabfee.component';

describe('TABFEE Management Component', () => {
  let comp: TABFEEComponent;
  let fixture: ComponentFixture<TABFEEComponent>;
  let service: TABFEEService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TABFEEComponent],
    })
      .overrideTemplate(TABFEEComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TABFEEComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TABFEEService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
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
    expect(comp.tABFEES?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
