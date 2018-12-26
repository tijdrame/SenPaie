/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { PrimeComponent } from '../../../../../../main/webapp/app/entities/prime/prime.component';
import { PrimeService } from '../../../../../../main/webapp/app/entities/prime/prime.service';
import { Prime } from '../../../../../../main/webapp/app/entities/prime/prime.model';

describe('Component Tests', () => {

    describe('Prime Management Component', () => {
        let comp: PrimeComponent;
        let fixture: ComponentFixture<PrimeComponent>;
        let service: PrimeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [PrimeComponent],
                providers: [
                    PrimeService
                ]
            })
            .overrideTemplate(PrimeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrimeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrimeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Prime(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.primes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
