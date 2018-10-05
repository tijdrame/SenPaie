/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenPaieTestModule } from '../../../test.module';
import { DetailPretDialogComponent } from '../../../../../../main/webapp/app/entities/detail-pret/detail-pret-dialog.component';
import { DetailPretService } from '../../../../../../main/webapp/app/entities/detail-pret/detail-pret.service';
import { DetailPret } from '../../../../../../main/webapp/app/entities/detail-pret/detail-pret.model';
import { CollaborateurService } from '../../../../../../main/webapp/app/entities/collaborateur';
import { PretService } from '../../../../../../main/webapp/app/entities/pret';
import { UserService } from '../../../../../../main/webapp/app/shared';

describe('Component Tests', () => {

    describe('DetailPret Management Dialog Component', () => {
        let comp: DetailPretDialogComponent;
        let fixture: ComponentFixture<DetailPretDialogComponent>;
        let service: DetailPretService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [DetailPretDialogComponent],
                providers: [
                    CollaborateurService,
                    PretService,
                    UserService,
                    DetailPretService
                ]
            })
            .overrideTemplate(DetailPretDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DetailPretDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DetailPretService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DetailPret(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.detailPret = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'detailPretListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DetailPret();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.detailPret = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'detailPretListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
