/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenPaieTestModule } from '../../../test.module';
import { MoisConcerneDetailComponent } from '../../../../../../main/webapp/app/entities/mois-concerne/mois-concerne-detail.component';
import { MoisConcerneService } from '../../../../../../main/webapp/app/entities/mois-concerne/mois-concerne.service';
import { MoisConcerne } from '../../../../../../main/webapp/app/entities/mois-concerne/mois-concerne.model';

describe('Component Tests', () => {

    describe('MoisConcerne Management Detail Component', () => {
        let comp: MoisConcerneDetailComponent;
        let fixture: ComponentFixture<MoisConcerneDetailComponent>;
        let service: MoisConcerneService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [MoisConcerneDetailComponent],
                providers: [
                    MoisConcerneService
                ]
            })
            .overrideTemplate(MoisConcerneDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MoisConcerneDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MoisConcerneService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new MoisConcerne(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.moisConcerne).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
