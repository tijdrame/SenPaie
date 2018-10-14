/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { TypePaiementComponent } from '../../../../../../main/webapp/app/entities/type-paiement/type-paiement.component';
import { TypePaiementService } from '../../../../../../main/webapp/app/entities/type-paiement/type-paiement.service';
import { TypePaiement } from '../../../../../../main/webapp/app/entities/type-paiement/type-paiement.model';

describe('Component Tests', () => {

    describe('TypePaiement Management Component', () => {
        let comp: TypePaiementComponent;
        let fixture: ComponentFixture<TypePaiementComponent>;
        let service: TypePaiementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [TypePaiementComponent],
                providers: [
                    TypePaiementService
                ]
            })
            .overrideTemplate(TypePaiementComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypePaiementComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypePaiementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TypePaiement(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.typePaiements[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
