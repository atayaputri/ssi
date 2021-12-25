import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { MAPSKSService } from '../service/mapsks.service';

import { MAPSKSComponent } from './mapsks.component';

describe('MAPSKS Management Component', () => {
  let comp: MAPSKSComponent;
  let fixture: ComponentFixture<MAPSKSComponent>;
  let service: MAPSKSService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [MAPSKSComponent],
    })
      .overrideTemplate(MAPSKSComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MAPSKSComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(MAPSKSService);

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
    expect(comp.mAPSKS?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
