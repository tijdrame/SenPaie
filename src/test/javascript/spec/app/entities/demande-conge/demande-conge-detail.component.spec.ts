/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenPaieTestModule } from '../../../test.module';
import { DemandeCongeDetailComponent } from '../../../../../../main/webapp/app/entities/demande-conge/demande-conge-detail.component';
import { DemandeCongeService } from '../../../../../../main/webapp/app/entities/demande-conge/demande-conge.service';
import { DemandeConge } from '../../../../../../main/webapp/app/entities/demande-conge/demande-conge.model';

describe('Component Tests', () => {

    describe('DemandeConge Management Detail Component', () => {
        let comp: DemandeCongeDetailComponent;
        let fixture: ComponentFixture<DemandeCongeDetailComponent>;
        let service: DemandeCongeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [DemandeCongeDetailComponent],
                providers: [
                    DemandeCongeService
                ]
            })
            .overrideTemplate(DemandeCongeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DemandeCongeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DemandeCongeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DemandeConge(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.demandeConge).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
