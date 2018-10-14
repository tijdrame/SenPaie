import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { SituationMatrimonialeComponent } from './situation-matrimoniale.component';
import { SituationMatrimonialeDetailComponent } from './situation-matrimoniale-detail.component';
import { SituationMatrimonialePopupComponent } from './situation-matrimoniale-dialog.component';
import { SituationMatrimonialeDeletePopupComponent } from './situation-matrimoniale-delete-dialog.component';

@Injectable()
export class SituationMatrimonialeResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const situationMatrimonialeRoute: Routes = [
    {
        path: 'situation-matrimoniale',
        component: SituationMatrimonialeComponent,
        resolve: {
            'pagingParams': SituationMatrimonialeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.situationMatrimoniale.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'situation-matrimoniale/:id',
        component: SituationMatrimonialeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.situationMatrimoniale.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const situationMatrimonialePopupRoute: Routes = [
    {
        path: 'situation-matrimoniale-new',
        component: SituationMatrimonialePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.situationMatrimoniale.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'situation-matrimoniale/:id/edit',
        component: SituationMatrimonialePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.situationMatrimoniale.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'situation-matrimoniale/:id/delete',
        component: SituationMatrimonialeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.situationMatrimoniale.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
