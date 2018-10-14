/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenPaieTestModule } from '../../../test.module';
import { TypePaiementDetailComponent } from '../../../../../../main/webapp/app/entities/type-paiement/type-paiement-detail.component';
import { TypePaiementService } from '../../../../../../main/webapp/app/entities/type-paiement/type-paiement.service';
import { TypePaiement } from '../../../../../../main/webapp/app/entities/type-paiement/type-paiement.model';

describe('Component Tests', () => {

    describe('TypePaiement Management Detail Component', () => {
        let comp: TypePaiementDetailComponent;
        let fixture: ComponentFixture<TypePaiementDetailComponent>;
        let service: TypePaiementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [TypePaiementDetailComponent],
                providers: [
                    TypePaiementService
                ]
            })
            .overrideTemplate(TypePaiementDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypePaiementDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypePaiementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TypePaiement(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.typePaiement).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
