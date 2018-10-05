/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenPaieTestModule } from '../../../test.module';
import { CategorieDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/categorie/categorie-delete-dialog.component';
import { CategorieService } from '../../../../../../main/webapp/app/entities/categorie/categorie.service';

describe('Component Tests', () => {

    describe('Categorie Management Delete Component', () => {
        let comp: CategorieDeleteDialogComponent;
        let fixture: ComponentFixture<CategorieDeleteDialogComponent>;
        let service: CategorieService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [CategorieDeleteDialogComponent],
                providers: [
                    CategorieService
                ]
            })
            .overrideTemplate(CategorieDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CategorieDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategorieService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
