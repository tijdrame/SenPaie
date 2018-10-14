import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { TypeAbsence } from './type-absence.model';
import { TypeAbsenceService } from './type-absence.service';

@Injectable()
export class TypeAbsencePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private typeAbsenceService: TypeAbsenceService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.typeAbsenceService.find(id)
                    .subscribe((typeAbsenceResponse: HttpResponse<TypeAbsence>) => {
                        const typeAbsence: TypeAbsence = typeAbsenceResponse.body;
                        this.ngbModalRef = this.typeAbsenceModalRef(component, typeAbsence);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.typeAbsenceModalRef(component, new TypeAbsence());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    typeAbsenceModalRef(component: Component, typeAbsence: TypeAbsence): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.typeAbsence = typeAbsence;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
