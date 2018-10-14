/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenPaieTestModule } from '../../../test.module';
import { TypeContratDetailComponent } from '../../../../../../main/webapp/app/entities/type-contrat/type-contrat-detail.component';
import { TypeContratService } from '../../../../../../main/webapp/app/entities/type-contrat/type-contrat.service';
import { TypeContrat } from '../../../../../../main/webapp/app/entities/type-contrat/type-contrat.model';

describe('Component Tests', () => {

    describe('TypeContrat Management Detail Component', () => {
        let comp: TypeContratDetailComponent;
        let fixture: ComponentFixture<TypeContratDetailComponent>;
        let service: TypeContratService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [TypeContratDetailComponent],
                providers: [
                    TypeContratService
                ]
            })
            .overrideTemplate(TypeContratDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeContratDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeContratService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TypeContrat(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.typeContrat).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
