/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { MoisConcerneComponent } from '../../../../../../main/webapp/app/entities/mois-concerne/mois-concerne.component';
import { MoisConcerneService } from '../../../../../../main/webapp/app/entities/mois-concerne/mois-concerne.service';
import { MoisConcerne } from '../../../../../../main/webapp/app/entities/mois-concerne/mois-concerne.model';

describe('Component Tests', () => {

    describe('MoisConcerne Management Component', () => {
        let comp: MoisConcerneComponent;
        let fixture: ComponentFixture<MoisConcerneComponent>;
        let service: MoisConcerneService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [MoisConcerneComponent],
                providers: [
                    MoisConcerneService
                ]
            })
            .overrideTemplate(MoisConcerneComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MoisConcerneComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MoisConcerneService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new MoisConcerne(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.moisConcernes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
